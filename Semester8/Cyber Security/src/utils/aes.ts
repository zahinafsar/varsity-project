// Helper Functions

// Convert a string to a byte array (UTF-8 encoded)
function stringToBytes(str) {
    let bytes = [];
    for (let i = 0; i < str.length; i++) {
        bytes.push(str.charCodeAt(i));
    }
    return bytes;
}

// Convert a byte array back to a string
function bytesToString(bytes) {
    return String.fromCharCode.apply(null, bytes);
}

// S-Box for SubBytes operation (simplified example, a full S-box would be used in practice)
const sBox = [
    0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76, 0xca,
    0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa8, 0x51, 0xa3, 0x40, 0x8f,
    // Complete S-box should be added here
];

// Key expansion (from key to round keys)
function keyExpansion(key) {
    const roundKeys = [];
    let keyWords = [];

    // Convert key to 4 words (4x4 matrix)
    for (let i = 0; i < 4; i++) {
        keyWords.push([key[4 * i], key[4 * i + 1], key[4 * i + 2], key[4 * i + 3]]);
    }

    let rConIndex = 0;
    while (keyWords.length < 44) {
        let temp = keyWords[keyWords.length - 1];

        if (keyWords.length % 4 === 0) {
            temp = temp.slice(1).concat(temp[0]);
            temp = temp.map(byte => sBox[byte]);
            temp[0] ^= (0x1b << rConIndex++);
        }

        keyWords.push(temp);
    }

    return keyWords;
}

// Convert a block (4x4 matrix) into a 1D array
function getBlock(state) {
    let block = [];
    for (let i = 0; i < 4; i++) {
        for (let j = 0; j < 4; j++) {
            block.push(state[j][i]);
        }
    }
    return block;
}

// Convert a 1D array into a 4x4 matrix (state)
function getState(block) {
    let state = [];
    for (let i = 0; i < 4; i++) {
        state[i] = [];
        for (let j = 0; j < 4; j++) {
            state[i][j] = block[i + 4 * j];
        }
    }
    return state;
}

// SubBytes operation (Substitute each byte using the S-Box)
function subBytes(state) {
    for (let i = 0; i < 4; i++) {
        for (let j = 0; j < 4; j++) {
            let row = (state[i][j] >> 4) & 0x0f;
            let col = state[i][j] & 0x0f;
            state[i][j] = sBox[row * 16 + col];
        }
    }
}

// ShiftRows operation (Shift rows of the state)
function shiftRows(state) {
    for (let i = 1; i < 4; i++) {
        state[i] = [...state[i].slice(i), ...state[i].slice(0, i)];
    }
}

// AddRoundKey operation (XOR the state with the round key)
function addRoundKey(state, roundKey) {
    for (let i = 0; i < 4; i++) {
        for (let j = 0; j < 4; j++) {
            state[i][j] ^= roundKey[i + 4 * j];
        }
    }
}

// AES encryption (using simplified operations for AES-128)
function aesEncrypt(plainText, key) {
    const roundKeys = keyExpansion(key);
    let state = getState(plainText);

    // Initial round
    addRoundKey(state, roundKeys.slice(0, 4));

    // Main rounds (9 rounds for AES-128)
    for (let round = 1; round < 10; round++) {
        subBytes(state);
        shiftRows(state);
        addRoundKey(state, roundKeys.slice(round * 4, (round + 1) * 4));
    }

    subBytes(state);
    shiftRows(state);
    addRoundKey(state, roundKeys.slice(40, 44)); // Final round

    return getBlock(state);
}

// AES decryption (simplified)
function aesDecrypt(cipherText, key) {
    const roundKeys = keyExpansion(key);
    let state = getState(cipherText);

    addRoundKey(state, roundKeys.slice(40, 44));

    for (let round = 9; round > 0; round--) {
        shiftRows(state);
        subBytes(state);
        addRoundKey(state, roundKeys.slice(round * 4, (round + 1) * 4));
    }

    shiftRows(state);
    subBytes(state);
    addRoundKey(state, roundKeys.slice(0, 4)); // Final round

    return getBlock(state);
}

// Example usage

const keyString = "thisisaverysecurekey"; // Key (128-bit)
const plainTextString = "Hello, this is a secret message!"; // Plaintext

// Convert key and plaintext to byte arrays
const keyBytes = stringToBytes(keyString).slice(0, 16); // Use only the first 16 bytes for AES-128
const plainTextBytes = stringToBytes(plainTextString.padEnd(16, ' ')); // Ensure the plaintext is 128-bit (16 bytes)

// Encrypt the plaintext
const cipherTextBytes = aesEncrypt(plainTextBytes, keyBytes);
console.log("Encrypted Ciphertext:", cipherTextBytes);

// Decrypt the ciphertext
const decryptedTextBytes = aesDecrypt(cipherTextBytes, keyBytes);
const decryptedTextString = bytesToString(decryptedTextBytes).trim(); // Convert byte array back to string and trim padding
console.log("Decrypted Text:", decryptedTextString);

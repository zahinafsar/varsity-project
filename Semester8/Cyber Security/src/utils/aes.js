// AES-128 Implementation
const SBOX = [
    0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
    0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
    0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
    0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
    0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
    0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
    0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
    0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
    0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
    0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
    0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
    0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
    0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
    0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
    0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
    0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
];

// Inverse S-box for decryption
const INV_SBOX = [
    0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb,
    0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb,
    0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e,
    0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25,
    0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92,
    0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84,
    0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06,
    0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b,
    0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73,
    0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e,
    0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b,
    0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4,
    0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f,
    0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef,
    0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61,
    0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d
];

const RCON = [
    [0x01, 0x00, 0x00, 0x00],
    [0x02, 0x00, 0x00, 0x00],
    [0x04, 0x00, 0x00, 0x00],
    [0x08, 0x00, 0x00, 0x00],
    [0x10, 0x00, 0x00, 0x00],
    [0x20, 0x00, 0x00, 0x00],
    [0x40, 0x00, 0x00, 0x00],
    [0x80, 0x00, 0x00, 0x00],
    [0x1b, 0x00, 0x00, 0x00],
    [0x36, 0x00, 0x00, 0x00]
];

export class AES {
    constructor(key) {
        if (typeof key === 'string') {
            key = this.stringToBytes(key);
        }
        this.expandKey(key);
    }

    // String/bytes conversion methods
    stringToBytes(str) {
        const bytes = new Uint8Array(16);
        for (let i = 0; i < Math.min(str.length, 16); i++) {
            bytes[i] = str.charCodeAt(i);
        }
        return bytes;
    }

    bytesToString(bytes) {
        let str = '';
        for (let i = 0; i < bytes.length; i++) {
            if (bytes[i] !== 0) { // Stop at first zero byte (padding)
                str += String.fromCharCode(bytes[i]);
            }
        }
        return str;
    }

    bytesToHex(bytes) {
        return Array.from(bytes).map(b => b.toString(16).padStart(2, '0')).join('');
    }

    // SubBytes Transformation
    subBytes(state) {
        for (let i = 0; i < 4; i++) {
            for (let j = 0; j < 4; j++) {
                state[i][j] = SBOX[state[i][j]];
            }
        }
        return state;
    }

    // Inverse SubBytes Transformation
    invSubBytes(state) {
        for (let i = 0; i < 4; i++) {
            for (let j = 0; j < 4; j++) {
                state[i][j] = INV_SBOX[state[i][j]];
            }
        }
        return state;
    }

    // ShiftRows Transformation
    shiftRows(state) {
        const temp = [];
        for (let i = 0; i < 4; i++) {
            temp[i] = state[i].slice();
        }

        for (let i = 1; i < 4; i++) {
            for (let j = 0; j < 4; j++) {
                state[i][j] = temp[i][(j + i) % 4];
            }
        }
        return state;
    }

    // Inverse ShiftRows Transformation
    invShiftRows(state) {
        const temp = [];
        for (let i = 0; i < 4; i++) {
            temp[i] = state[i].slice();
        }

        for (let i = 1; i < 4; i++) {
            for (let j = 0; j < 4; j++) {
                state[i][j] = temp[i][(j - i + 4) % 4];
            }
        }
        return state;
    }

    // MixColumns Transformation
    mixColumns(state) {
        for (let i = 0; i < 4; i++) {
            const a = state[0][i];
            const b = state[1][i];
            const c = state[2][i];
            const d = state[3][i];

            state[0][i] = this.gmul(0x02, a) ^ this.gmul(0x03, b) ^ c ^ d;
            state[1][i] = a ^ this.gmul(0x02, b) ^ this.gmul(0x03, c) ^ d;
            state[2][i] = a ^ b ^ this.gmul(0x02, c) ^ this.gmul(0x03, d);
            state[3][i] = this.gmul(0x03, a) ^ b ^ c ^ this.gmul(0x02, d);
        }
        return state;
    }

    // Inverse MixColumns Transformation
    invMixColumns(state) {
        for (let i = 0; i < 4; i++) {
            const a = state[0][i];
            const b = state[1][i];
            const c = state[2][i];
            const d = state[3][i];

            state[0][i] = this.gmul(0x0e, a) ^ this.gmul(0x0b, b) ^ this.gmul(0x0d, c) ^ this.gmul(0x09, d);
            state[1][i] = this.gmul(0x09, a) ^ this.gmul(0x0e, b) ^ this.gmul(0x0b, c) ^ this.gmul(0x0d, d);
            state[2][i] = this.gmul(0x0d, a) ^ this.gmul(0x09, b) ^ this.gmul(0x0e, c) ^ this.gmul(0x0b, d);
            state[3][i] = this.gmul(0x0b, a) ^ this.gmul(0x0d, b) ^ this.gmul(0x09, c) ^ this.gmul(0x0e, d);
        }
        return state;
    }

    // Galois Field multiplication
    gmul(a, b) {
        let p = 0;
        for (let i = 0; i < 8; i++) {
            if ((b & 1) !== 0) {
                p ^= a;
            }
            const hi_bit_set = (a & 0x80) !== 0;
            a <<= 1;
            if (hi_bit_set) {
                a ^= 0x1b;
            }
            b >>= 1;
        }
        return p & 0xff;
    }

    // Add Round Key Transformation
    addRoundKey(state, roundKey) {
        for (let i = 0; i < 4; i++) {
            for (let j = 0; j < 4; j++) {
                state[i][j] ^= roundKey[i][j];
            }
        }
        return state;
    }

    // Key Expansion
    expandKey(key) {
        this.roundKeys = new Array(11);
        for (let i = 0; i < 11; i++) {
            this.roundKeys[i] = Array(4).fill().map(() => Array(4).fill(0));
        }

        // First round key is the key itself
        for (let i = 0; i < 4; i++) {
            for (let j = 0; j < 4; j++) {
                this.roundKeys[0][i][j] = key[i * 4 + j];
            }
        }

        // Generate the rest of the round keys
        for (let round = 1; round < 11; round++) {
            for (let i = 0; i < 4; i++) {
                let temp = this.roundKeys[round - 1][i].slice();
                if (i === 0) {
                    temp = this.rotWord(temp);
                    temp = this.subWord(temp);
                    temp = this.xorWords(temp, RCON[round - 1]);
                }
                this.roundKeys[round][i] = this.xorWords(this.roundKeys[round - 1][i], temp);
            }
        }
    }

    // Helper functions for Key Expansion
    rotWord(word) {
        const temp = word[0];
        for (let i = 0; i < 3; i++) {
            word[i] = word[i + 1];
        }
        word[3] = temp;
        return word;
    }

    subWord(word) {
        for (let i = 0; i < 4; i++) {
            word[i] = SBOX[word[i]];
        }
        return word;
    }

    xorWords(word1, word2) {
        const result = [];
        for (let i = 0; i < 4; i++) {
            result[i] = word1[i] ^ word2[i];
        }
        return result;
    }

    // Encrypt a 16-byte block or string
    encrypt(input) {
        if (typeof input === 'string') {
            input = this.stringToBytes(input);
        }

        let state = Array(4).fill().map(() => Array(4).fill(0));
        
        // Convert input to state array
        for (let i = 0; i < 4; i++) {
            for (let j = 0; j < 4; j++) {
                state[i][j] = input[i * 4 + j];
            }
        }

        // Initial round
        state = this.addRoundKey(state, this.roundKeys[0]);

        // Main rounds
        for (let round = 1; round < 10; round++) {
            state = this.subBytes(state);
            state = this.shiftRows(state);
            state = this.mixColumns(state);
            state = this.addRoundKey(state, this.roundKeys[round]);
        }

        // Final round (no MixColumns)
        state = this.subBytes(state);
        state = this.shiftRows(state);
        state = this.addRoundKey(state, this.roundKeys[10]);

        // Convert state array to output
        const output = new Uint8Array(16);
        for (let i = 0; i < 4; i++) {
            for (let j = 0; j < 4; j++) {
                output[i * 4 + j] = state[i][j];
            }
        }

        return output;
    }

    // Decrypt a 16-byte block or hex string
    decrypt(input) {
        if (typeof input === 'string') {
            // Convert hex string to bytes
            input = new Uint8Array(input.match(/.{1,2}/g).map(byte => parseInt(byte, 16)));
        }

        let state = Array(4).fill().map(() => Array(4).fill(0));
        
        // Convert input to state array
        for (let i = 0; i < 4; i++) {
            for (let j = 0; j < 4; j++) {
                state[i][j] = input[i * 4 + j];
            }
        }

        // Initial round
        state = this.addRoundKey(state, this.roundKeys[10]);
        state = this.invShiftRows(state);
        state = this.invSubBytes(state);

        // Main rounds
        for (let round = 9; round >= 1; round--) {
            state = this.addRoundKey(state, this.roundKeys[round]);
            state = this.invMixColumns(state);
            state = this.invShiftRows(state);
            state = this.invSubBytes(state);
        }

        // Final round
        state = this.addRoundKey(state, this.roundKeys[0]);

        // Convert state array to output
        const output = new Uint8Array(16);
        for (let i = 0; i < 4; i++) {
            for (let j = 0; j < 4; j++) {
                output[i * 4 + j] = state[i][j];
            }
        }

        return output;
    }
}
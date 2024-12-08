chrome.runtime.onInstalled.addListener(async () => {
  chrome.contextMenus.create({
    title: "SPM",
    id: "parent",
    contexts: ["all"],
  });
});

chrome.contextMenus.onClicked.addListener(function (info, tab) {
  chrome.scripting.executeScript({
    target: { tabId: tab.id },
    files: ["./build/modal/app.js"],
  });
  chrome.scripting.insertCSS({
    target: { tabId: tab.id },
    files: ["./build/modal/app.css"],
  });
  chrome.scripting.executeScript({
    target: { tabId: tab.id },
    files: ["./build/style/global.js"],
  });
  chrome.scripting.insertCSS({
    target: { tabId: tab.id },
    files: ["./build/style/global.css"],
  });
});

import { ACTIONS } from "./constant/index.mjs";

chrome.runtime.onInstalled.addListener(async () => {
  chrome.contextMenus.create({
    title: "Quint",
    id: "parent",
    contexts: ["page", "selection"],
  });
  Object.entries(ACTIONS).forEach(([_, value]) => {
    chrome.contextMenus.create({
      title: value.title,
      id: value.id,
      parentId: "parent",
      contexts: ["selection"],
    });
  });
});

chrome.contextMenus.onClicked.addListener(function (info, tab) {
  chrome.scripting.executeScript({
    target: { tabId: tab.id },
    func: runAction,
    args: [info],
  });
  chrome.scripting.executeScript({
    target: { tabId: tab.id },
    files: ["./build/bundle.js"],
  });
  chrome.scripting.insertCSS({
    target: { tabId: tab.id },
    files: ["./src/style/global.css"],
  });
});

const runAction = (action) => {
  sessionStorage.setItem("spm-action", action.menuItemId);
};

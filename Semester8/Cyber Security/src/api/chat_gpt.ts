import { api } from ".";
const API_KEY = "####";
const GPT_BASE_URL = "https://api.openai.com/v1";

const config = {
  "Content-Type": "application/json",
  Authorization: `Bearer ${API_KEY}`,
}

interface IMessage {
  id: string,
  object: string,
  created: string,
  model: string,
  usage:
  {
    prompt_tokens: number,
    completion_tokens: number,
    total_tokens: number
  },
  choices: [
    {
      message:
      {
        role: string,
        content: string
      },
      finish_reason: string,
      index: number
    }
  ]
}
export const _sendMessage = async (message) => {
  return api.post<IMessage>(`${GPT_BASE_URL}/chat/completions`, {
    model: "gpt-3.5-turbo",
    messages: [{ role: "user", content: message }],
  }, config);
};

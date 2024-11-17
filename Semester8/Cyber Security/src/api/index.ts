const BASE_URL = "http://localhost:9090/api";

export const httpClient = async <T>(method, url, body, header): Promise<T> => {
  const api_url = url.includes("http") ? url : `${BASE_URL}${url}`;
  return new Promise(async (resolve, reject) => {
    const res = await fetch(api_url, {
      method,
      headers: {
        "Content-Type": "application/json",
        ...header,
      },
      body: JSON.stringify(body),
    });
    const result = await res.json();
    if (res.ok) {
      resolve(result);
    } else {
      reject(result);
    }
  })
};

type IHttpClientMethod = <T>(url: string, body: any, header?: any) => Promise<T>;
interface IHttpClient {
  post: IHttpClientMethod
  get: IHttpClientMethod
  put: IHttpClientMethod
  patch: IHttpClientMethod
  delete: IHttpClientMethod
}

export const api: IHttpClient = {
  post: (url, body, header) => httpClient("POST", url, body, header),
  get: (url, body, header) => httpClient("GET", url, body, header),
  put: (url, body, header) => httpClient("PUT", url, body, header),
  patch: (url, body, header) => httpClient("PATCH", url, body, header),
  delete: (url, body, header) => httpClient("DELETE", url, body, header),
};
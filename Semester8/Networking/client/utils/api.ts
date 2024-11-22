const API_BASE_URL = 'http://localhost:8080';

type FetchOptions = RequestInit & {
  params?: Record<string, string>;
};

async function fetchApi(endpoint: string, options: FetchOptions = {}) {
  const { params, ...fetchOptions } = options;
  
  let url = `${API_BASE_URL}${endpoint}`;
  
  if (params) {
    const searchParams = new URLSearchParams();
    Object.entries(params).forEach(([key, value]) => {
      searchParams.append(key, value);
    });
    url += `?${searchParams.toString()}`;
  }

  const response = await fetch(url, {
    ...fetchOptions,
    headers: {
      'Content-Type': 'application/json',
      ...fetchOptions.headers,
    },
  });

  if (!response.ok) {
    throw new Error(`API Error: ${response.status} ${response.statusText}`);
  }

  return response.json();
}

export const api = {
  get: (endpoint: string, options?: Omit<FetchOptions, 'method'>) => 
    fetchApi(endpoint, { ...options, method: 'GET' }),
    
  post: (endpoint: string, data?: any, options?: Omit<FetchOptions, 'method' | 'body'>) =>
    fetchApi(endpoint, {
      ...options,
      method: 'POST',
      body: JSON.stringify(data),
    }),

  put: (endpoint: string, data?: any, options?: Omit<FetchOptions, 'method' | 'body'>) =>
    fetchApi(endpoint, {
      ...options, 
      method: 'PUT',
      body: JSON.stringify(data),
    }),

  delete: (endpoint: string, options?: Omit<FetchOptions, 'method'>) =>
    fetchApi(endpoint, { ...options, method: 'DELETE' }),
};

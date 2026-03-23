export const environment = {
  production: (window as any)["env"]?.["production"] === 'true' || false,
  apiUrl: (window as any)["env"]?.["apiUrl"] || 'http://localhost:30082/payments'
};

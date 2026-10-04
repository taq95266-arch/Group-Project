
export interface User {
  token: string;
  email: string;
  fullName: string;
  role: string;
  roles?: string[];
}
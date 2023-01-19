export interface Credentials {
  login: string,
  password: string
}

export interface Token {
  jwt: string;
  email: string;
  role: string;
}

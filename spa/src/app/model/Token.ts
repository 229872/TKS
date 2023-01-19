export interface Token {
  jwt: string;
}

export interface TokenInfo {
  jwt: string,
  userType: string,
  sub: string
}

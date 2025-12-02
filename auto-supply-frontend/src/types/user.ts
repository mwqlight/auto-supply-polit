export interface LoginParams {
  username: string
  password: string
}

export interface LoginResponse {
  code: number
  message: string
  data: {
    accessToken: string
    tokenType: string
    expiresIn: number
  }
  timestamp: number
}

export interface UserInfo {
  id: string
  username: string
  nickname: string
  avatar: string
  roles: string[]
  permissions: string[]
}
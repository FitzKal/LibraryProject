import type {Book} from "./Book.ts";

export interface User {
    user_id?: number,
    username:string,
    password?: string,
    recipes?:Book[],
    role?: "ADMIN" | "USER",
    accessToken?: string;
}

export type UserAuthRequest = {
    username : string,
    password: string
}

export type UserResponse = {
    userId?:number,
    username:string,
    role?: "ADMIN" | "USER",
}

export type userProp = {
    userInfo : UserResponse
}
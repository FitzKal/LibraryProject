export type AuthFormFields ={
    username: string,
    password: string
}

export type PostBookFormFields = {
    title:string,
    pictureSRC: string,
    description:string,
    genre?:"FANTASY" | "THRILLER" | "SCIFI" | "ROMANCE",
}

export type BookRequest = {
    id?:number
    title:string,
    pictureSRC: string,
    description:string,
    author:string,
    genre?:"FANTASY" | "THRILLER" | "SCIFI" | "ROMANCE",
    username:string
}

export type UserEditRequest = {
    username:string,
    role:"ADMIN" | "USER"
}

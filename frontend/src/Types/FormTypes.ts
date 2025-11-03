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
    title:string,
    pictureSRC: string,
    description:string,
    author:string,
    genre?:"FANTASY" | "THRILLER" | "SCIFI" | "ROMANCE",
    username:string
}

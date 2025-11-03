
export interface Book{
    id?:number,
    title:string,
    description?:string,
    pictureSRC?:string,
    genre?:"FANTASY" | "THRILLER" | "SCIFI" | "ROMANCE",
    username:string
}

export type book = {
    id?:number,
    title:string,
    description?:string,
    pictureSRC?:string,
    genre?:"FANTASY" | "THRILLER" | "SCIFI" | "ROMANCE",
    username:string
}

export type bookProp = {
    bookInfo: Book;
}
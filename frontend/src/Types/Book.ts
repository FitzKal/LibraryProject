
export interface Book{
    id?:number,
    title:string,
    description?:string,
    author:string,
    pictureSRC?:string,
    genre?:"FANTASY" | "THRILLER" | "SCIFI" | "ROMANCE",
    username:string
}

export type book = {
    id?:number,
    title:string,
    author:string,
    description?:string,
    pictureSRC?:string,
    genre?:"FANTASY" | "THRILLER" | "SCIFI" | "ROMANCE",
    username:string
}

export type bookElementProp = {
    bookInfo: Book;
    setUpdating : (book:book)=> void;
}

export type bookProp = {
    bookInfo: Book
}
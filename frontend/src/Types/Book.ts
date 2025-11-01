
export interface Book{
    id?:number,
    title:string,
    description?:string,
    pictureSrc?:string,
    genre?:"FANTASY" | "THRILLER" | "SCIFI" | "ROMANCE",
    username:string
}
// ------------- GetAll -------------
import type {BookRequest} from "../Types/FormTypes.ts";

export const getAllBooks = async (accessToken:string) =>{
    const res = await fetch("/api/books",{
        headers:{Authorization:`Bearer ${accessToken}`},
    });
    if (res.ok){
        const response = await res.json();
        console.log(response);
        return response;
    }else {
        const message = await res.text();
        throw new Error(message || "Request could not be completed");
    }
}

// ------------- GetById -------------

export const getBookById = async (accessToken:string, bookId: number | undefined) =>{
    const res = await fetch(`/api/books/${bookId}`,{
        headers: {Authorization : `Bearer ${accessToken}`},
    });
    if (res.ok){
        const response = await res.json();
        console.log(response);
        return response;
    }else {
        const message = await res.text();
        throw new Error(message || "Request could not be completed");
    }
}

// ------------- PostBook -------------

export const postBook = async (accessToken:string,data:BookRequest, username:string) =>{
    data.username = username;
    const res = await fetch("/api/books",{
        method: "POST",
        headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type" : "application/json",
        },
        body:JSON.stringify(data)
    });
    if (res.ok){
        const response = await res.json();
        console.log(response);
        return response;
    }else {
        const message = await res.text();
        throw new Error(message || "Request could not be completed");
    }
}

// ------------- DeleteBook -------------

export const deleteBook = async (accessToken:string ,id:number) => {
    const res = await fetch(`/api/books/${id}`,{
        method: "DELETE",
        headers:{Authorization: `Bearer ${accessToken}`}
    });
    if (res.ok){
        return await res.text();
    }else{
        const error = await res.text();
        throw new Error(error || "Request could not be completed");
    }
}
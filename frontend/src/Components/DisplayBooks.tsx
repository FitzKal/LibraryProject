import {useEffect} from "react";
import {getAllBooks} from "../Services/BookService.ts";
import {userStore} from "../Stores/UserStore.ts";
import {useQuery} from "@tanstack/react-query";
import {toast} from "react-toastify";
import Book from "./Book.tsx";
import type {book} from "../Types/Book.ts";

export default function DisplayBooks(){

    const currentUser = userStore.getState().user;

    const {data, error, isError, isLoading} = useQuery({
        queryKey: ["books"],
        queryFn: async () =>{
            if (!currentUser?.accessToken){
                throw new Error("Could not authenticate");
            }
            return await getAllBooks(currentUser.accessToken);
        },
        enabled: !!currentUser?.accessToken,
    });

    useEffect(()=>{
       if (isError && error instanceof Error){
           toast.error(error.message);
       }
    },[isError,error])




    return isLoading ? (
        <p>Loading...</p>
    ):(<div>
            {data.map((book:book) =>
                <Book key = {book.id} bookInfo = {book}/>
            )}
        </div>
    );
}
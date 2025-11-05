import {userStore} from "../../Stores/UserStore.ts";
import {type MouseEventHandler, useEffect, useState} from "react";
import type {book, Book} from "../../Types/Book.ts";
import {useQuery} from "@tanstack/react-query";
import {getUserBooks} from "../../Services/BookService.ts";
import {toast} from "react-toastify";
import PostBookForm from "./PostBookForm.tsx";
import UpdateBookForm from "./UpdateBookForm.tsx";
import BookElement from "./BookElement.tsx";

export default function MyBooks(){
    const currentUser = userStore.getState().user;

    const [isPosting, setPosting] = useState<boolean>(false);
    const [isUpdating, setUpdating] = useState<boolean>(false);
    const [toUpdate, setToUpdate] = useState<Book>();

    const {data, error, isError, isLoading} = useQuery({
        queryKey: ["books"],
        queryFn: async () =>{
            if (!currentUser?.accessToken){
                throw new Error("Could not authenticate");
            }
            return await getUserBooks(currentUser.accessToken);
        },
        enabled: !!currentUser?.accessToken,
    });

    useEffect(()=>{
        if (isError && error instanceof Error){
            toast.error(error.message);
        }
    },[isError,error])

    const handlePosting:MouseEventHandler<HTMLButtonElement> = () =>{
        if (!isPosting){
            setPosting(true);
        }else {
            setPosting(false);
        }
    }

    const handleUpdating = (book?:Book) =>{
        if (!isUpdating){
            setUpdating(true);
            setToUpdate(book);
        }else {
            setUpdating(false);
        }
    }


    return isLoading ? (
        <p>Loading...</p>
    ):(<div className={"m-auto"}>
            <button className={"text-xl border-2 rounded-2xl pl-2 pr-2 mb-10 mt-10 ml-20 bg-blue-200 transition delay-75 ease-in-out hover:bg-blue-400"}
                    onClick={handlePosting}>Add book</button>
            {(isPosting) ? <PostBookForm /> : <></>}
            {(isUpdating)?<UpdateBookForm bookInfo={toUpdate} manageEditing = {handleUpdating}/>:<></>}
            <div className={"flex flex-wrap justify-center flex-row gap-15 mt-10"}>
                {data.map((book:book) =>
                    <BookElement key={book.id} bookInfo={book} setUpdating={handleUpdating} />
                )}
            </div>
        </div>
    );
}
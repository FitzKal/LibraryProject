import {type MouseEventHandler, useEffect, useState} from "react";
import {deleteBook, getAllBooks} from "../../Services/BookService.ts";
import {userStore} from "../../Stores/UserStore.ts";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {toast} from "react-toastify";
import type {book} from "../../Types/Book.ts";
import PostBookForm from "./PostBookForm.tsx";
import BookElement from "./BookElement.tsx";

export default function DisplayBooks(){

    const currentUser = userStore.getState().user;
    const queryClient = useQueryClient();

    const [isPosting, setPosting] = useState<boolean>(false);

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

    const handleEditing:MouseEventHandler<HTMLButtonElement> = () =>{
        if (!isPosting){
            setPosting(true);
        }else {
            setPosting(false);
        }

    }

    const deleteMutation = useMutation({
        mutationFn:(data: number) => deleteBook(currentUser.accessToken,data),
        onSuccess:() => {
            toast.success("The chosen book has been deleted");
            queryClient.invalidateQueries({queryKey:["books"]});
        },
        onError:(error) =>{
            if (error instanceof Error){
                console.log(error.message);
                toast.error(error.message);
            } else {
                toast.error("Something went wrong");
            }
        }
    })

    const handleDelete = (id:number) =>{
        deleteMutation.mutate(id);
    }

    return isLoading ? (
        <p>Loading...</p>
    ):(<div className={" ml-6 mr-6"}>
            <button className={"text-xl border-2 rounded-2xl pl-2 pr-2 mb-10 mt-10 ml-20 bg-blue-200 transition delay-75 ease-in-out hover:bg-blue-400"}
            onClick={handleEditing}>Add book</button>
            {(isPosting) ? <PostBookForm/> : <></>}
            <div className={"flex flex-wrap flex-row gap-15 mt-10"}>
                {data.map((book:book) =>
                    <BookElement key={book.id} bookInfo={book} onDelete={handleDelete} />
                )}

            </div>
        </div>
    );
}
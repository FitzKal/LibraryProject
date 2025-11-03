import type {bookElementProp} from "../../Types/Book.ts";
import Book from "./Book.tsx";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {deleteBook} from "../../Services/BookService.ts";
import {toast} from "react-toastify";
import {userStore} from "../../Stores/UserStore.ts";

export default function BookElement({bookInfo}:bookElementProp){

    const currentUser = userStore.getState().user;
    const queryClient = useQueryClient();

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

    return(<div>
        <Book bookInfo={bookInfo}/>
        <button className={"text-l border-2 rounded-2xl pl-1 pr-1 w-20 bg-red-800 " +
            "text-white transition delay-50 ease-in-out hover:bg-red-500"}
                onClick={()=>handleDelete(bookInfo.id)}>Delete</button>
        <button className={"text-l border-2 rounded-2xl ml-10 pl-1 pr-1 w-20 bg-blue-800 " +
            "text-white transition delay-50 ease-in-out hover:bg-blue-500"}>Edit</button>
    </div>);
}
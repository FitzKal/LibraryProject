import {useParams} from "react-router-dom"
import {useQuery} from "@tanstack/react-query";
import {userStore} from "../../Stores/UserStore.ts";
import {getBookById} from "../../Services/BookService.ts";
import {useEffect} from "react";
import {toast} from "react-toastify";
export default function BookPage(){
     const {param} =  useParams();
     const id = param !== undefined ? parseInt(param) : undefined;
    const currentUser =userStore.getState().user;
    const {data, error, isError, isLoading} = useQuery({
        queryKey : ["BookById"],
        queryFn: async () =>{
            if (!currentUser?.accessToken){
                throw new Error("Could not authenticate");
            }
            if (id ===undefined){
                throw new Error("Could not find the requested book");
            }
            return await getBookById(currentUser.accessToken, id);
        },
        enabled: !!currentUser?.accessToken
    });

    useEffect(()=>{
        if (isError && error instanceof Error){
            toast.error(error.message);
        }
    },[isError,error])


    return isLoading ? (
        <p>Loading...</p>
    ): (
        <div className={"flex flex-col"}>
            <h1 className={"text-4xl text-center mb-8 mt-8"}>{data.title}</h1>
            <div className={"flex justify-center mb-3"}>
                <img className={"h-120 w-80 transition delay-150 duration-300 ease-in-out hover:scale-130"} src={data.pictureSRC} alt={data.title}/>
            </div>
            <div className={"text-center flex flex-col gap-1"}>
                <h2 className={"text-center text-xl"}>Written by: <strong className={"text-blue-700"}>{data.author}</strong></h2>
                <p>Genre: <strong className={"text-blue-700"}>{data.genre}</strong></p>
                <p>Added by: <strong className={"text-blue-700"}>{data.username}</strong></p>
            </div>
            <div className={"mt-5 ml-3 mr-3"}>
                <h2 className={"text-2xl"}><strong>Description:</strong></h2>
                <p>{data.description}</p>
            </div>

        </div>
    );
}
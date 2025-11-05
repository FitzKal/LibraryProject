import {userStore} from "../../Stores/UserStore.ts";
import {useQuery} from "@tanstack/react-query";
import {toast} from "react-toastify";
import {useEffect} from "react";
import type {UserResponse} from "../../Types/User.ts";
import User from "./User.tsx";
import {usersGetAll} from "../../Services/AdminService.ts";


export default function DisplayUsers(){
    const currentUser = userStore.getState().user;

    const {data,error,isError,isLoading} = useQuery<UserResponse[]>({
        queryKey:["users"],
        queryFn: async () =>{
            if (!currentUser?.accessToken){
                throw new Error("Could not authenticate");
            }
            return await usersGetAll(currentUser.accessToken);
        },
        enabled: !!currentUser?.accessToken,
    });
    useEffect(()=>{
        if (isError && error instanceof Error){
            toast.error(error.message);
        }
    },[isError,error])

    useEffect(()=>{
        console.log(data);
    })

    return isLoading ? (
        <p>Loading...</p>
    ):(<div>
        {data.map((user:UserResponse) =>
        <User key={user.userId} userInfo = {user} />
            )}
    </div>)
}
import {userStore} from "../../Stores/UserStore.ts";
import {useQuery} from "@tanstack/react-query";
import {toast} from "react-toastify";
import {useEffect, useState} from "react";
import type {UserResponse} from "../../Types/User.ts";
import User from "./User.tsx";
import {usersGetAll} from "../../Services/AdminService.ts";
import UserEditForm from "./UserEditForm.tsx";


export default function DisplayUsers(){

    const [isUpdating, setIsUpdating] = useState<boolean>(false);
    const [userToUpdate, setUserToUpdate] = useState<UserResponse>();

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

    const handleUpdating = (user:UserResponse)=>{
        if (!isUpdating){
            setIsUpdating(true);
            setUserToUpdate(user);
        }else {
            setIsUpdating(false);
        }
    }

    return isLoading ? (
        <p>Loading...</p>
    ):(<div className={"flex flex-col"}>
        {isUpdating ? <UserEditForm userToUpdate = {userToUpdate}  handleClose = {handleUpdating}/> : <></>}
        <div className="flex justify-center w-auto ml-3">
            <table className="table-auto w-250 border-collapse">
                <thead className={"border-b-2"}>
                <tr>
                    <th scope="col" className="text-left p-2 border-b">ID</th>
                    <th scope="col" className="text-left p-2 border-b">Name</th>
                    <th scope="col" className="text-left p-2 border-b">Role</th>
                </tr>
                </thead>
                <tbody className={"border-b-2"}>
                {(data ?? []).map(u => (
                    <User key={u.userId} userInfo={u} handleUpdating={handleUpdating} />
                ))}
                </tbody>
            </table>
        </div>
    </div>)
}
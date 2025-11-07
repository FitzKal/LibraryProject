import type {userProp} from "../../Types/User.ts";
import {userStore} from "../../Stores/UserStore.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {userDelete} from "../../Services/AdminService.ts";
import {toast} from "react-toastify";


export default function User(prop:userProp){
    const currentUser = userStore.getState().user;
    const queryClient = useQueryClient();

    const deleteMutation = useMutation({
        mutationFn: (id:number) => {
            const token = userStore.getState().user?.accessToken;
            if (!token) {
                throw new Error("Not authenticated");
            }
            return userDelete(token, id);
        },
        onSuccess:() =>{
            toast.success("Delete successful");
            queryClient.invalidateQueries({queryKey:["users"]})
        },
        onError: (error) =>{
            if (error instanceof Error){
                toast.error(error.message);
            }else {
                toast.error("Could not complete request");
            }
        }
    })

    const handleDelete = (id:number) =>{
        deleteMutation.mutate(id);
    }

    const isOwnUser = currentUser?.username === prop.userInfo.username;
    const canDelete = !isOwnUser && prop.userInfo.userId != null;

    return(
        <tr className="border-b-2 bg-blue-100">
            <td className="px-2 py-1 ">{prop.userInfo.userId}</td>
            <td className="px-2 py-1 ">{prop.userInfo.username}</td>
            <td className="px-2 py-1 ">{prop.userInfo.role}</td>
            <td><button className={"rounded-2xl pr-2 pl-2 bg-blue-800 " +
                "text-white transition delay-50 ease-in-out hover:bg-blue-500"}
            onClick={()=> prop.handleUpdating(prop.userInfo)}>Update</button></td>
            <td>{canDelete ? <button className={"pr-2 pl-2 rounded-2xl w-20 bg-red-800 " +
                "text-white transition delay-50 ease-in-out hover:bg-red-500"} onClick={() => handleDelete(prop.userInfo.userId!)}>Delete</button>
            :<></>}</td>
        </tr>
    );
}
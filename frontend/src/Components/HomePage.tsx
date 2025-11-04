import {userStore} from "../Stores/UserStore.ts";
import {useNavigate} from "react-router-dom";
import type {MouseEventHandler} from "react";
import {useMutation} from "@tanstack/react-query";
import {userLogout} from "../Services/AuthService.ts";
import {toast} from "react-toastify";

export default function HomePage(){
    const navigate = useNavigate();
    const currentUser = userStore.getState().user;

    const logoutMutation = useMutation({
        mutationFn: () => userLogout(currentUser.accessToken),
        onSuccess:() =>{
            userStore.getState().stateLogout();
            navigate("/login");
            toast.success("Successfully logged out!");
        },
        onError:(error) => {
            if (error instanceof Error){
                toast.error(error.message);

            }else{
                toast.error("Could not complete request");
            }
        }
    })
    const handleLogout: MouseEventHandler<HTMLButtonElement> = () => {
        logoutMutation.mutate();
    };

    return(
        <div>
            <h1 className={"text-[5rem] text-center mt-60"}>Welcome <strong className={"text-blue-700"}>{userStore.getState().user.username}</strong></h1>
            <div className={"flex justify-center mt-20"}>
                <button className={"text-2xl border-2 rounded-2xl pl-2 pr-2 bg-red-800 " +
                    "text-white transition delay-50 ease-in-out hover:bg-red-500"}
                onClick={handleLogout}>Log out</button>
            </div>
        </div>
    )
}
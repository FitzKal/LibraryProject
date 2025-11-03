import {userStore} from "../Stores/UserStore.ts";
import {useNavigate} from "react-router-dom";
import type {MouseEventHandler} from "react";

export default function HomePage(){
    const navigate = useNavigate();
    const handleLogout: MouseEventHandler<HTMLButtonElement> = () => {
        userStore.getState().stateLogout();
        navigate("/login");
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
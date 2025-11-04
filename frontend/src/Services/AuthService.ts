// ------------- LOGIN -------------
import type {UserAuthRequest} from "../Types/User.ts";

export const userLogin = async (data : UserAuthRequest) =>{
    const res = await fetch("/api/authenticate/login",{
        method:'POST',
        headers:{
            "Content-Type" : "application/json",
        },
        body:JSON.stringify(data)
    });
    if (res.ok){
        const response =  await res.json();
        console.log(response);
        return response;
    }else{
        const text = await res.text();
        throw new Error(text||"Request could not be completed!");
    }

}

export const userRegister = async (data: UserAuthRequest) =>{
    const res = await fetch("/api/authenticate/register",{
        method: 'POST',
            headers:{
            "Content-Type":"application/json",
            },
        body:JSON.stringify(data)
    });
    if (res.ok){
        const response = await res.json();
        console.log(response);
        return response;
    }else {
        const text = await res.text();
        throw new Error(text || "Request could not be completed");
    }
}

// ------------- LOGOUT -------------
export const userLogout = async (accessToken: string) =>{
    const res = await fetch("/api/authenticate/logout",{
        method: 'POST',
        headers:{
            Authorization: `Bearer ${accessToken}`,
            "Content-Type" : "application/json",
        },
    });
    if(res.ok){
           return;
    } else{
        const message = await res.text();
        throw new Error(message || "Could not complete request");
    }
}
import {type SubmitHandler, useForm} from 'react-hook-form';
import type {AuthFormFields} from "../Types/FormTypes.ts";
import {useNavigate, Link} from 'react-router-dom';
import "../styles/AuthForms.css"
import {useMutation} from "@tanstack/react-query";
import type {UserAuthRequest} from "../Types/User.ts";
import {userLogin} from "../Services/AuthService.ts";
import {userStore} from "../Stores/UserStore.ts";
import {toast} from "react-toastify";


export default function LoginForm(){
    const {register,handleSubmit,formState:{errors,isSubmitting}} = useForm<AuthFormFields>();

    const onSubmit: SubmitHandler<AuthFormFields> = async (data) =>{
        mutation.mutate(data);
    }

    const mutation = useMutation({
        mutationFn:(data:UserAuthRequest)=>
            userLogin(data),
        onSuccess:(result,variable) =>{
            userStore.getState().stateLogin({
                accessToken: result.accessToken,
                username: variable.username,
                role: result.role
            })
            navigate("/home");
            toast.success("Login Successful");
    },
        onError:(error) =>{
            if (error instanceof Error){
                toast.error(error.message);
            }else {
                toast.error("Something went wrong");
            }
        }
    })

    const navigate = useNavigate();
    return (
        <div className={"container"}>
            <h1 className={"HeaderText text-4xl font-semibold"}>Sign in</h1>
            <div className={"FormContainer"}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className={"formBody"}>
                        <input {...register("username",{
                            required : "Username is required",
                            minLength: 4
                        })} type={"text"} placeholder={"Username"} className={"textInput"}/>
                        {errors.username &&( <div className={"text-red-500"}>{errors.username.message}</div>)}
                        <input {...register("password",{
                            required :"Password is required"
                        })} type={"password"} placeholder={"Password"} className={"textInput"}/>
                        {errors.password &&( <div className={"text-red-500"}>{errors.password.message}</div>)}
                        <button type={"submit"} disabled={isSubmitting} className={"submitButton"}>
                            {isSubmitting ? "Loading..." : "Submit"}
                        </button>
                    </div>
                </form>
            </div>
            <Link className={"redirectAuthLink text-blue-600"} to={"/register"}>New to the website? Register!</Link>
        </div>
    );
}
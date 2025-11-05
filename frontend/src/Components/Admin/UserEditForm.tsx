import {type SubmitHandler, useForm} from "react-hook-form";
import type {UserEditRequest} from "../../Types/FormTypes.ts";
import {userStore} from "../../Stores/UserStore.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {toast} from "react-toastify";
import type {UserResponse} from "../../Types/User.ts";
import {userUpdate} from "../../Services/AdminService.ts";

export default function UserEditForm(props:{userToUpdate:UserResponse|undefined,handleClose:(user:UserResponse) => void}){

    const {register,handleSubmit,formState:{errors,isSubmitting}} = useForm<UserEditRequest>({
        defaultValues :{
            username:props.userToUpdate?.username,
            role:props.userToUpdate?.role
        }
    });
    const currentUser = userStore.getState().user;
    const queryClient = useQueryClient();

    const updateMutation = useMutation({
        mutationFn:(data:UserEditRequest) =>
            userUpdate(currentUser.accessToken,props.userToUpdate?.userId,data),
        onSuccess:() =>{
            toast.success("The chosen user was updated!");
            queryClient.invalidateQueries({queryKey:["users"]});
        },
        onError:(error)=>{
            if (error instanceof Error){
                toast.error(error.message);
            }else{
                toast.error("Something went wrong");
            }
        }

    })

    const onSubmit: SubmitHandler<UserEditRequest> = async (data:UserEditRequest) => {
        updateMutation.mutate(data)
    }


    return (
        <div className={"flex flex-col justify-center  mb-5"}>
            <h1 className={"text-center mr-10 text-xl mb-3"}>Update an existing book</h1>
            <div className={"flex border-2 rounded-2xl self-center p-2 w-250 bg-yellow-100"}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className={"flex gap-3 flex-wrap"}>
                        <div className={"flex flex-col"}>
                            <input{...register("username",{
                                required:"Username is required",
                                minLength:3,
                            })} type={"text"} placeholder={"title"} className={"border-2 bg-white text-center w-75"} />
                            {errors.username &&( <div className={"text-red-500 w-auto"}>{errors.username.message}</div>)}
                        </div>
                        <label>Choose a genre: </label>
                        <select{...register("role",{
                            required:true,
                        })} className={"bg-white border-2 max-h-lh"}>
                            <option value="USER" >User</option>
                            <option value="ADMIN">Admin</option>
                        </select>
                        <button type={"submit"} className={"border-2 pr-2 pl-2 ml-1 bg-blue-400 transition delay-75 ease-in-out hover:bg-blue-600 max-h-7 w-30"}
                        >Update</button>
                        <button className={"border-2 pr-2 pl-2 ml-1 bg-red-500 transition delay-75 ease-in-out hover:bg-red-600 max-h-7 w-30"}
                         onClick={()=>props.handleClose(props.userToUpdate)}>Close</button>
                    </div>
                </form>
            </div>
        </div>
    );
}
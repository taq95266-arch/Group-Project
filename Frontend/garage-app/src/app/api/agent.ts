
import axios, { type AxiosResponse } from "axios";
import type { FieldValues } from "react-hook-form";



axios.defaults.baseURL = 'http://localhost:8080/api/';
axios.defaults.withCredentials = false;



axios.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if(token){
       config.headers.Authorization = `Bearer ${token}`; 
    }
    return config;
})



const responsBody = (response: AxiosResponse) => response.data;




// Api
const requests = {
    get: (url: string, params?: URLSearchParams) => axios.get(url, {params}).then(responsBody),
    post: (url: string, body: object) => axios.post(url, body).then(responsBody),
    put: (url: string, body: object) => axios.put(url, body).then(responsBody),
    delete: (url: string) => axios.delete(url).then(responsBody),
    postForm: (url: string, data: FormData) => axios.post(url, data, {
        headers: { 'Content-type': 'multipart/form-data' }
    }).then(responsBody),
    putForm: (url: string, data: FormData) => axios.put(url, data, {
        headers: { 'Content-type': 'multipart/form-data' }
    }).then(responsBody)

}


// const TestErros = {
//     get400Error: () => requests.get('buggy/bad-request'),
//     get401Error: () => requests.get('buggy/unauthorized'),
//     get404Error: () => requests.get('buggy/not-found'),
//     get500Error: () => requests.get('buggy/server-error'),
//     getValidationError: () => requests.get('buggy/validation-error'),

// }

const Account = {
    login : (values :FieldValues) => requests.post('auth/login',values),
    currentUser: () => requests.get('auth/current-user'),


}

const Owner = {
    registerDocumnet : (values :FieldValues) => requests.post('owner/registration/register-owner',values)

}





const agent = {
    Account,
    Owner
}

export default agent;







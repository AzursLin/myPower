import axios from 'axios';


export let  baseurl = 'http://localhost:4040/';


//export const requestLogin = params => { return axios.post(`${base}/login`, params).then(res => res.data); };
export const requestLogin = params => { return axios.post(`${baseurl}/vrbicycle/cmsClub!Login.action?appPhaseII=Club`, params).then(res => res.data); };

//export const getUserList = params => { return axios.get(`${base}/user/list`, { params: params }); };
export const login = params => { return axios.post(`${baseurl}/login`, params).then(res => res.data); };

export const getUserList = params => { return axios.get(`${baseurl}/user/list`, { params: params }); };

export const userAdd = params => { return axios.get(`${baseurl}/user/list/add`, { params: params }); };

export const userUpdata = params => { return axios.get(`${baseurl}/user/list/update`, { params: params }); };

export const userDelete = params => { return axios.get(`${baseurl}/user/list/delete`, { params: params }); };

export const getDormList = params => { return axios.get(`${baseurl}/dorm/list`, { params: params }); };

export const dormAdd = params => { return axios.get(`${baseurl}/dorm/list/add`, { params: params }); };

export const dormUpdata = params => { return axios.get(`${baseurl}/dorm/list/update`, { params: params }); };

export const dormDelete = params => { return axios.get(`${baseurl}/dorm/list/delete`, { params: params }); };

export const getUserElectricity = params => { return axios.get(`${baseurl}/ele/list`, { params: params }); };

export const userElectricityAdd = params => { return axios.get(`${baseurl}/ele/list/add`, { params: params }); };

export const userElectricityUpdata = params => { return axios.get(`${baseurl}/ele/list/update`, { params: params }); };

export const userElectricityDelete = params => { return axios.get(`${baseurl}/ele/list/delete`, { params: params }); };


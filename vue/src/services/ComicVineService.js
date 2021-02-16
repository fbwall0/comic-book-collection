import axios from 'axios';

const http = axios.create({
  baseURL: "https://gateway.marvel.com/v1/public/"
});


export default {
    list() {
        return http.get('comics?title=X-Men%20Unlimited&issueNumber=3&ts=thesoer&apikey=e3f13dcf4ff77039ddccc9f959b733d9&hash=3fefa0949426eff3dc9adad144d10133');
    },

}
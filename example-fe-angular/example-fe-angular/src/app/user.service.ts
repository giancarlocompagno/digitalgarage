import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient:HttpClient) { }

  url = "http://localhost:8084/esempio/saluta/"
  getUser(text: string){
    return this.httpClient.get(`${this.url}${text}`);
  }
}
//ng g service user

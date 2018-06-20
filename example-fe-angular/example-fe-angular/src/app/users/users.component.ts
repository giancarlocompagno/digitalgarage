import { Component, OnInit } from '@angular/core';
import {User} from "./user";
import {UserService} from "../user.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  constructor(private userService:UserService) { }
  prova = {pluto:"pippo"}
  users : User;

  ngOnInit() {
    // this.userService.getUser().subscribe((data: User)=> {
    //   console.log(data, typeof data);
    //   this.users = data;
    // })
  }

  loadUser(text: string){
    this.userService.getUser(text).subscribe((data: User) => {
      this.users = data;
      console.log(this.nome)
    })
  }

}

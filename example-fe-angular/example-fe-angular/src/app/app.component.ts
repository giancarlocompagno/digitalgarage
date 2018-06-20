import {Component, OnInit, Output} from '@angular/core';
import {User} from "./users/user";
import {UserService} from "./user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  constructor(private userService: UserService){}

  ngOnInit(): void {
    this.userService.getUser().subscribe(function (data: User) {
      console.log(data, data.constructor.name);
      this.users = data;
      console.log(this.users.messaggio)
    })
  }

}

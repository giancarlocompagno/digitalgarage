import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {HeroesComponent} from "./heroes/heroes.component";
import {IndexComponent} from "./index/index.component";
import {UsersComponent} from "./users/users.component";

const routes: Routes = [
  { path: 'heroes', component: HeroesComponent },
  { path: '', component: IndexComponent },
  { path: 'user', component: UsersComponent }
];

@NgModule({
  imports: [
    CommonModule,
     RouterModule.forRoot(routes, { useHash: true })
  ],
  declarations: [],
  exports: [ RouterModule ]
})

export class AppRoutingModule { }
//ng generate module app-routing --flat --module=app

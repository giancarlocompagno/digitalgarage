import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {HeroesComponent} from "./heroes/heroes.component";
import {IndexComponent} from "./index/index.component";

const routes: Routes = [
  { path: 'heroes', component: HeroesComponent },
  { path: '', component: IndexComponent }
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

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { CardsComponent } from './cards/cards.component';
import { CarouselComponent } from './carousel/carousel.component';
import { AccordionsComponent } from './accordions/accordions.component';
import { AppRoutingModule } from './/app-routing.module';
import { HeroesComponent } from './heroes/heroes.component';
import { IndexComponent } from './index/index.component';
import {HttpClientModule} from "@angular/common/http";
import { UsersComponent } from './users/users.component';

@NgModule({
  declarations: [
    AppComponent,
    CardsComponent,
    CarouselComponent,
    AccordionsComponent,
    HeroesComponent,
    IndexComponent,
    UsersComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

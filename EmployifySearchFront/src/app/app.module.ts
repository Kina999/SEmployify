import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar/navbar.component';
import { SearchComponent } from './search/search/search.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SearchBoolComponent } from './search-bool/search-bool/search-bool.component';
import { SearchGeoComponent } from './search-geo/search-geo/search-geo.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { GeoapifyGeocoderAutocompleteModule } from '@geoapify/angular-geocoder-autocomplete';
import { StatisticsComponent } from './statistics/statistics/statistics.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SearchComponent,
    SearchBoolComponent,
    SearchGeoComponent,
    StatisticsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    GeoapifyGeocoderAutocompleteModule.withConfig('4a570a3a30fc44b5a54d9920bba1030f'),
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

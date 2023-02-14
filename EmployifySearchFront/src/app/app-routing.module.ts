import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from 'src/app/search/search/search.component';
import { SearchBoolComponent } from './search-bool/search-bool/search-bool.component';
import { SearchGeoComponent } from './search-geo/search-geo/search-geo.component';

const routes: Routes = [{path: '', component: SearchComponent},
                        {path: 'bool', component: SearchBoolComponent},
                        {path: 'geo', component: SearchGeoComponent}
                      ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from 'src/app/search/search/search.component';
import { SearchBoolComponent } from './search-bool/search-bool/search-bool.component';
import { SearchGeoComponent } from './search-geo/search-geo/search-geo.component';
import { StatisticsComponent } from './statistics/statistics/statistics.component';

const routes: Routes = [{path: '', component: SearchComponent},
                        {path: 'bool', component: SearchBoolComponent},
                        {path: 'geo', component: SearchGeoComponent},
                        {path: 'statistics', component: StatisticsComponent}
                      ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }

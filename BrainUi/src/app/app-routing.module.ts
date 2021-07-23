import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ResultsComponent} from "./results/results.component";
import {ViewComponent} from "./view/view.component";


const routes: Routes = [
  {path: 'results', component: ResultsComponent},
  {path: '', component: ViewComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

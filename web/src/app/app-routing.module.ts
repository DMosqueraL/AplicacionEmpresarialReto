import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AnswerComponent} from './paginas/answer/answer.component';
import {EditComponent} from './paginas/edit/edit.component';
import {RequestionComponent} from './paginas/requestion/requestion.component';
import {LoginComponent} from './persona/login/login.component';
import {PreguntasComponent} from './persona/preguntas/preguntas.component';
import {RegistroComponent} from './persona/registro/registro.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'preguntas', component: PreguntasComponent},
  { path: 'registro', component: RegistroComponent},
  { path: 'answer', component: AnswerComponent},
  { path: 'ediatar', component: EditComponent},
  {path: 'question/:id', component: RequestionComponent},
  { path: 'forgot-password', loadChildren: () => import('./persona/forgot-password/forgot-password.module').then(m => m.ForgotPasswordModule) },
  {path: '**', pathMatch: 'full', redirectTo:'preguntas'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }




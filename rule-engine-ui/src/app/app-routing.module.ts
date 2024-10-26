import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RuleListComponent } from './components/rule-list/rule-list.component';
import { RuleCreateComponent } from './components/rule-create/rule-create.component';
import { RuleCombineComponent } from './components/rule-combine/rule-combine.component';
import { RuleEvaluateComponent } from './components/rule-evaluate/rule-evaluate.component';

const routes: Routes = [
  { path: '', redirectTo: '/rules', pathMatch: 'full' },
  { path: 'rules', component: RuleListComponent },
  { path: 'create', component: RuleCreateComponent },
  { path: 'combine', component: RuleCombineComponent },
  { path: 'evaluate', component: RuleEvaluateComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

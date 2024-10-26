import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { RuleListComponent } from './components/rule-list/rule-list.component';
import { RuleCreateComponent } from './components/rule-create/rule-create.component';
import { RuleCombineComponent } from './components/rule-combine/rule-combine.component';
import { RuleEvaluateComponent } from './components/rule-evaluate/rule-evaluate.component';
import { RulePageComponent } from './pages/rule-page/rule-page.component';
import { RuleService } from './services/rule.service';


@NgModule({
  declarations: [
    AppComponent,
    RuleListComponent,
    RuleCreateComponent,
    RuleCombineComponent,
    RuleEvaluateComponent,
    RulePageComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCheckboxModule,
    AppRoutingModule
  ],
  providers: [
    provideAnimationsAsync(),
    RuleService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

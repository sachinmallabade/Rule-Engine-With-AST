import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rule } from '../models/rule.model';


export interface CombineRequest {
  rules: string[];
}


@Injectable({
  providedIn: 'root'
})
export class RuleService {
  private baseUrl = 'http://localhost:8085/api/rules';

  constructor(private http: HttpClient) {}

  getRules(): Observable<Rule[]> {
    return this.http.get<Rule[]>(`${this.baseUrl}`);
  }

  createRule(rule: { ruleString: string }): Observable<Rule> {
    return this.http.post<Rule>(`${this.baseUrl}/create`, rule);
  }

  combineRules(requestBody: CombineRequest): Observable<any> {
        return this.http.post<any>(`${this.baseUrl}/combine`, requestBody);
    }
  

    getAST(ruleId: number): Observable<any> {
      return this.http.get<any>(`${this.baseUrl}/${ruleId}/ast`);
    }

    evaluateRule(requestBody: any): Observable<any> {
      return this.http.post<any>(`${this.baseUrl}/evaluate`, requestBody);
    }
}

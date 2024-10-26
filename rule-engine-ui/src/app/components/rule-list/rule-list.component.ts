import { Component, OnInit } from '@angular/core';
import { RuleService } from '../../services/rule.service';
import { Rule } from '../../models/rule.model';

@Component({
  selector: 'app-rule-list',
  templateUrl: './rule-list.component.html',
  styleUrls: ['./rule-list.component.css']
})
export class RuleListComponent implements OnInit {
  rules: Rule[] = [];

  constructor(private ruleService: RuleService) {}

  ngOnInit(): void {
    this.loadRules();
  }

  loadRules(): void {
    this.ruleService.getRules().subscribe(
      (data) => {
        this.rules = data;
      },
      (error) => {
        console.error('Error fetching rules:', error);
      }
    );
  }
}

import { Component } from '@angular/core';
import { RuleService } from '../../services/rule.service';

@Component({
  selector: 'app-rule-create',
  templateUrl: './rule-create.component.html',
  styleUrls: ['./rule-create.component.css'],
})
export class RuleCreateComponent {
  ruleString: string = '';
  ast: any = null;
  errorMessage: string = '';

  constructor(private ruleService: RuleService) {}

  onSubmit() {
    // Clear previous results
    this.errorMessage = '';  // Clear previous error messages
    this.ast = null;         // Clear previous AST

    // Basic validation for empty ruleString
    if (!this.ruleString.trim()) {
      this.errorMessage = 'Please enter a rule string.';
      return;
    }

    // Make the API call to create the rule
    this.ruleService.createRule({ ruleString: this.ruleString }).subscribe({
      next: (response) => {
        // Check if the response indicates a valid AST
        if (response && typeof response === 'object') {
          this.ast = response;  // Set AST if the rule is valid
          this.errorMessage = '';  // Clear any error messages
        } else {
          this.errorMessage = 'Invalid rule string. Please check your input.';
          this.ast = null;  // Clear AST in case of an error
        }
      },
      error: (error) => {
        // Display a generic error message if the API call fails
        this.errorMessage = 'Invalid rule string. Please check your input.';
        this.ast = null;  // Clear AST in case of an error
        console.error('Error response from backend:', error);
      },
    });
  }
}

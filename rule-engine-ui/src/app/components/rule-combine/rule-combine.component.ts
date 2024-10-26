import { Component, QueryList, ViewChildren, ElementRef } from '@angular/core';
import { RuleService } from '../../services/rule.service';

@Component({
  selector: 'app-rule-combine',
  templateUrl: './rule-combine.component.html',
  styleUrls: ['./rule-combine.component.css']
})
export class RuleCombineComponent {
  ruleStrings: string[] = []; // Holds user-defined rule strings
  combinedRule: string = '';
  errorMessage: string = ''; // For meaningful error messages

  @ViewChildren('ruleInput') ruleInputs!: QueryList<ElementRef>;

  constructor(private ruleService: RuleService) {}

  addRule(): void {
    this.ruleStrings.push(''); // Add an empty string for user input
    setTimeout(() => {
      const lastInput = this.ruleInputs.last.nativeElement;
      lastInput.focus(); // Set focus on the last input
    });
  }

  deleteRule(index: number): void {
    this.ruleStrings.splice(index, 1); // Remove the rule at the given index
    if (this.ruleStrings.length === 0) {
      this.combinedRule = ''; // Clear the combined rule if no input exists
      this.errorMessage = ''; // Clear the error message
    }
  }

  sanitizeRule(rule: string): string {
    // Add space around operators >, <, = if not present
    return rule.replace(/([><=]+)/g, ' $1 ').replace(/\s+/g, ' ').trim();
  }

  combineRules(): void {
    if (this.ruleStrings.length === 0) {
      alert('Please enter at least one rule string.');
      return;
    }

    this.errorMessage = ''; // Reset the error message before combining

    // Sanitize the input before sending it to the backend
    const sanitizedRules = this.ruleStrings.map(rule => this.sanitizeRule(rule));
    const requestBody = { rules: sanitizedRules };

    // Make the API call to combine rules
    this.ruleService.combineRules(requestBody).subscribe(
      (response) => {
        if (response) {
          this.combinedRule = response.type ? JSON.stringify(response, null, 2) : ''; // Display AST as JSON string
          this.errorMessage = ''; // Clear error message if success
        } else {
          this.combinedRule = '';
          this.errorMessage = 'Invalid rules provided, please check the input.';
        }
      },
      (error) => {
        console.error('Error combining rules:', error);
        this.errorMessage = 'An error occurred while combining rules. Please check the rules and try again.';
      }
    );
  }

  trackByIndex(index: number, obj: any): any {
    return index; // Optimize rendering
  }
}

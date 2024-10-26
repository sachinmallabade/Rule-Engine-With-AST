import { Component } from '@angular/core';
import { RuleService } from '../../services/rule.service';

@Component({
  selector: 'app-rule-evaluate',
  templateUrl: './rule-evaluate.component.html',
  styleUrls: ['./rule-evaluate.component.css']
})
export class RuleEvaluateComponent {
  ruleId!: number;
  age!: number;
  department!: string;
  salary!: number;
  experience!: number;
  evaluationResults: boolean[] | null = null; // To hold results
  errorMessage: string | null = null; // To hold error message

  constructor(private ruleService: RuleService) {}

  evaluateRule() {
    this.errorMessage = null; // Reset error message before a new request
    this.ruleService.getAST(this.ruleId).subscribe(ast => {
        if (!ast) {
            this.errorMessage = 'AST not found for the given rule ID.';
            return;
        }
       
        const requestBody = {
            ast: ast, // Use the fetched AST
            data: [
                {
                    age: this.age,
                    department: this.department,
                    salary: this.salary,
                    experience: this.experience
                }
            ]
        };

        console.log('Request Body:', requestBody); // Debug: log the request body

        this.ruleService.evaluateRule(requestBody).subscribe(
            response => {
                this.evaluationResults = response.results; // Store results in the property
            },
            error => {
                console.error('Error evaluating rule:', error);
                this.evaluationResults = null; // Reset results on error
                this.errorMessage = 'Error evaluating the rule: ' + (error.error?.message || error.message || 'Unknown error'); // Set error message
            }
        );
    }, error => {
            
            if (error.status === 404) {
                this.errorMessage = 'Rule ID not found.'; 
            } else {
                this.errorMessage = 'Error fetching AST: ' + (error.error?.message || error.message || 'Unknown error'); // General error handling
            }
            console.error('Error fetching AST:', error);
        }
    );
}

}

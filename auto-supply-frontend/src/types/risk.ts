export interface Risk {
  id: number
  title: string
  description: string
  category: string
  severity: 'low' | 'medium' | 'high' | 'critical'
  likelihood: number
  impact: number
  riskScore: number
  status: 'identified' | 'assessed' | 'mitigated' | 'monitored' | 'closed'
  owner: string
  department: string
  identifiedDate: string
  reviewedDate?: string
  dueDate?: string
  mitigationPlan?: string
  contingencyPlan?: string
  relatedProcesses: number[]
  relatedSuppliers: number[]
  createdAt: string
  updatedAt: string
}

export interface RiskAssessment {
  id: number
  riskId: number
  assessor: string
  assessmentDate: string
  methodology: string
  likelihood: number
  impact: number
  riskScore: number
  notes: string
  attachments: Array<{
    id: number
    name: string
    url: string
    uploadedAt: string
  }>
}

export interface RiskMitigation {
  id: number
  riskId: number
  action: string
  description: string
  responsibleParty: string
  startDate: string
  dueDate: string
  completionDate?: string
  status: 'not_started' | 'in_progress' | 'completed' | 'delayed'
  cost: number
  effectiveness: number
}

export interface RiskQueryParams {
  page?: number
  size?: number
  keyword?: string
  category?: string
  severity?: string
  status?: string
  department?: string
}

export interface RiskAssessmentQueryParams {
  page?: number
  size?: number
  riskId?: number
  assessor?: string
}
export type CategoriaSolicitacao = "ILUMINACAO" | "BURACO" | "LIXO" | "PODA";

export type StatusSolicitacao = "ABERTA" | "EM_ANDAMENTO" | "RESOLVIDA";

export type HistoricoStatus = {
  status: StatusSolicitacao;
  data: string;
  observacao: string;
};

export type Solicitacao = {
  id: string;
  cidadaoId: string;
  titulo: string;
  descricao: string;
  bairro: string;
  categoria: string;
  status: StatusSolicitacao;
  historicoStatus?: HistoricoStatus[];
};

export type MudarStatusPayload = {
  status: StatusSolicitacao;
  observacao?: string;
};

export type SolicitacaoPayload = {
  cidadaoId: string;
  titulo: string;
  descricao: string;
  bairro: string;
  categoria: CategoriaSolicitacao;
};

export const CATEGORIAS: CategoriaSolicitacao[] = [
  "ILUMINACAO",
  "BURACO",
  "LIXO",
  "PODA",
];

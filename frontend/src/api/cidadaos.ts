import { apiFetch } from "./client";
import type { Cidadao, CidadaoPayload } from "../types/cidadao";

export function listarCidadaos(): Promise<Cidadao[]> {
  return apiFetch<Cidadao[]>("/cidadaos");
}

export function criarCidadao(payload: CidadaoPayload): Promise<Cidadao> {
  return apiFetch<Cidadao>("/cidadaos", {
    method: "POST",
    body: JSON.stringify(payload),
  });
}

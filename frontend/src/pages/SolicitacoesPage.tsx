import { useCallback, useEffect, useMemo, useState, type FormEvent } from "react";
import { Link } from "react-router-dom";
import { listarCidadaos } from "../api/cidadaos";
import { ApiError } from "../api/client";
import { criarSolicitacao, listarSolicitacoes } from "../api/solicitacoes";
import type { Cidadao } from "../types/cidadao";
import {
  CATEGORIAS,
  type CategoriaSolicitacao,
  type Solicitacao,
} from "../types/solicitacao";

const formularioVazio = () => ({
  cidadaoId: "",
  titulo: "",
  descricao: "",
  bairro: "",
  categoria: "ILUMINACAO" as CategoriaSolicitacao,
});

export function SolicitacoesPage() {
  const [cidadaos, setCidadaos] = useState<Cidadao[]>([]);
  const [solicitacoes, setSolicitacoes] = useState<Solicitacao[]>([]);
  const [carregando, setCarregando] = useState(true);
  const [erroLista, setErroLista] = useState<string | null>(null);

  const [form, setForm] = useState(formularioVazio);
  const [erroForm, setErroForm] = useState<string | null>(null);
  const [salvando, setSalvando] = useState(false);

  const nomesPorId = useMemo(() => {
    const map = new Map<string, string>();
    for (const c of cidadaos) {
      map.set(c.id, c.nome);
    }
    return map;
  }, [cidadaos]);

  const recarregar = useCallback(async () => {
    setCarregando(true);
    setErroLista(null);
    try {
      const [listaSol, listaCid] = await Promise.all([
        listarSolicitacoes(),
        listarCidadaos(),
      ]);
      setSolicitacoes(listaSol);
      setCidadaos(listaCid);
    } catch (e) {
      setErroLista(
        e instanceof ApiError ? e.message : "Não foi possível carregar dados.",
      );
    } finally {
      setCarregando(false);
    }
  }, []);

  useEffect(() => {
    void recarregar();
  }, [recarregar]);

  async function enviar(e: FormEvent) {
    e.preventDefault();
    if (!form.cidadaoId) {
      setErroForm("Selecione o cidadão responsável.");
      return;
    }
    setErroForm(null);
    setSalvando(true);
    try {
      await criarSolicitacao({
        cidadaoId: form.cidadaoId,
        titulo: form.titulo.trim(),
        descricao: form.descricao.trim(),
        bairro: form.bairro.trim(),
        categoria: form.categoria,
      });
      setForm(formularioVazio());
      await recarregar();
    } catch (err) {
      setErroForm(
        err instanceof ApiError
          ? err.message
          : "Não foi possível cadastrar a solicitação.",
      );
    } finally {
      setSalvando(false);
    }
  }

  return (
    <div className="space-y-8">
      <div>
        <h1 className="text-2xl font-semibold text-emerald-900">Solicitações</h1>
        <p className="mt-1 text-sm text-slate-600">
          Cada ocorrência fica vinculada a um cidadão cadastrado.
        </p>
      </div>

      <section className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
        <h2 className="text-lg font-medium">Nova solicitação</h2>
        {cidadaos.length === 0 && !carregando ? (
          <p className="mt-3 text-sm text-amber-800">
            Cadastre um cidadão em{" "}
            <Link to="/cidadaos" className="font-medium underline">
              Cidadãos
            </Link>{" "}
            antes de abrir uma solicitação.
          </p>
        ) : (
          <form onSubmit={enviar} className="mt-4 grid gap-4 sm:grid-cols-2">
            <label className="block text-sm sm:col-span-2">
              <span className="text-slate-600">Cidadão responsável *</span>
              <select
                required
                value={form.cidadaoId}
                onChange={(ev) =>
                  setForm({ ...form, cidadaoId: ev.target.value })
                }
                className="mt-1 w-full rounded-lg border border-slate-300 px-3 py-2"
              >
                <option value="">— Selecione —</option>
                {cidadaos.map((c) => (
                  <option key={c.id} value={c.id}>
                    {c.nome} ({c.email})
                  </option>
                ))}
              </select>
            </label>
            <label className="block text-sm sm:col-span-2">
              <span className="text-slate-600">Título *</span>
              <input
                required
                type="text"
                value={form.titulo}
                onChange={(ev) => setForm({ ...form, titulo: ev.target.value })}
                className="mt-1 w-full rounded-lg border border-slate-300 px-3 py-2"
              />
            </label>
            <label className="block text-sm sm:col-span-2">
              <span className="text-slate-600">Descrição *</span>
              <textarea
                required
                rows={3}
                value={form.descricao}
                onChange={(ev) =>
                  setForm({ ...form, descricao: ev.target.value })
                }
                className="mt-1 w-full rounded-lg border border-slate-300 px-3 py-2"
              />
            </label>
            <label className="block text-sm">
              <span className="text-slate-600">Bairro *</span>
              <input
                required
                type="text"
                value={form.bairro}
                onChange={(ev) => setForm({ ...form, bairro: ev.target.value })}
                className="mt-1 w-full rounded-lg border border-slate-300 px-3 py-2"
              />
            </label>
            <label className="block text-sm">
              <span className="text-slate-600">Categoria *</span>
              <select
                value={form.categoria}
                onChange={(ev) =>
                  setForm({
                    ...form,
                    categoria: ev.target.value as CategoriaSolicitacao,
                  })
                }
                className="mt-1 w-full rounded-lg border border-slate-300 px-3 py-2"
              >
                {CATEGORIAS.map((cat) => (
                  <option key={cat} value={cat}>
                    {cat}
                  </option>
                ))}
              </select>
            </label>
            {erroForm && (
              <p className="rounded-lg bg-amber-50 px-3 py-2 text-sm text-amber-900 sm:col-span-2">
                {erroForm}
              </p>
            )}
            <p className="sm:col-span-2">
              <button
                type="submit"
                disabled={salvando || cidadaos.length === 0}
                className="rounded-lg bg-emerald-700 px-4 py-2 text-sm font-medium text-white hover:bg-emerald-800 disabled:opacity-50"
              >
                {salvando ? "Salvando…" : "Cadastrar solicitação"}
              </button>
            </p>
          </form>
        )}
      </section>

      <section className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
        <h2 className="text-lg font-medium">Solicitações cadastradas</h2>
        {erroLista && (
          <p className="mt-2 rounded-lg bg-red-50 px-3 py-2 text-sm text-red-800">
            {erroLista}
          </p>
        )}
        {carregando ? (
          <p className="mt-4 text-sm text-slate-500">Carregando…</p>
        ) : solicitacoes.length === 0 ? (
          <p className="mt-4 text-sm text-slate-500">Nenhuma solicitação ainda.</p>
        ) : (
          <div className="mt-4 overflow-x-auto">
            <table className="w-full min-w-[40rem] text-left text-sm">
              <thead>
                <tr className="border-b border-slate-200 text-slate-500">
                  <th className="py-2 pr-3 font-medium">Título</th>
                  <th className="py-2 pr-3 font-medium">Cidadão</th>
                  <th className="py-2 pr-3 font-medium">Bairro</th>
                  <th className="py-2 pr-3 font-medium">Categoria</th>
                  <th className="py-2 pr-3 font-medium">Status</th>
                  <th className="py-2 font-medium">Detalhe</th>
                </tr>
              </thead>
              <tbody>
                {solicitacoes.map((s) => (
                  <tr key={s.id} className="border-b border-slate-100">
                    <td className="py-2 pr-3">{s.titulo}</td>
                    <td className="py-2 pr-3">
                      {nomesPorId.get(s.cidadaoId) ?? s.cidadaoId}
                    </td>
                    <td className="py-2 pr-3">{s.bairro}</td>
                    <td className="py-2 pr-3">{s.categoria}</td>
                    <td className="py-2 pr-3">{s.status}</td>
                    <td className="py-2">
                      <Link
                        to={`/solicitacoes/${encodeURIComponent(s.id)}`}
                        className="text-emerald-700 hover:underline"
                      >
                        Abrir
                      </Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </section>
    </div>
  );
}
